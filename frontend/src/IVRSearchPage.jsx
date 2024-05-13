import React, { useState, useEffect, useCallback } from 'react'
import BackArrowList from './components/BackArrowList'
import { useNavigate } from 'react-router-dom'
import VideoCam from './components/VideoCam'
import { socket } from './components/connectToModal'
import VariantWord from './components/variantWord'

const IVRSearchPage = () => {
    const navigate = useNavigate()
    const [words, setWords] = useState([])
    const [start, setStart] = useState(false)
    let videoElement;
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    const [intervalID, setIntervalID] = useState();

    function deleteWord(text) {
        setWords(words.filter(el => el.id != text))
    }



    const stopAllTracks = (stream) => {
        stream &&
            stream.getTracks().forEach((track) => {
                if (track.readyState == 'live')
                    track.stop();
            });
    }

    const onConnectToModal = useCallback(() => {
        console.log("Connected to server");
    }, [])

    const onDisconnectFromModal = useCallback(() => {
        console.log("Disconnect");
        // socket.connect()
    }, [])

    const receiveText = useCallback((text) => {
        let result = Object.values(JSON.parse(text))[0]
        let obj = {
            text: result,
            id: Math.random() * Math.random()
        }
        setWords([...words, obj])
    })

    const addFrameSender = useCallback(() => {
        let id = setInterval(() => {
            // console.log("Send frame")
            let newWidth = 224;
            let newHeight = 224;

            canvas.width = 224;
            canvas.height = 224;
            context?.drawImage(videoElement, 0, (224 - newHeight) / 2, newWidth, newHeight);
            const image = canvas.toDataURL('image/jpeg');
            socket.emit("data", image);
        }, 30)
        setIntervalID(id)
    }, [context, canvas, videoElement, setIntervalID])

    const startWebcam = useCallback(async (addFrameSender) => {
        try {
            stopAllTracks(videoElement.srcObject)
            videoElement.srcObject = await navigator.mediaDevices.getUserMedia({ video: { facingMode: "user" } });
            videoElement.addEventListener('play', addFrameSender, { once: true });
        } catch (error) {
            console.error('Error accessing webcam:', error);
        }

        return () => {
            videoElement.removeEventListener('play', addFrameSender, { once: true });
            stopAllTracks(videoElement.srcObject)
        }
    }, [videoElement, intervalID])

    useEffect(() => {
        socket.on("send_not_normalize_text", receiveText);
        return () => {
            socket.off("send_not_normalize_text", receiveText)
        }
    }, [receiveText]);

    useEffect(() => {


        videoElement = document.getElementById('webcam');
        if (videoElement)
            startWebcam(addFrameSender);
        return () => {
            socket.off("connect", onConnectToModal);
            socket.off("disconnect", onDisconnectFromModal);
            socket.disconnect();
            videoElement.removeEventListener('play', addFrameSender);
            stopAllTracks(videoElement.srcObject)
        }

    }, []);

    useEffect(() => {
        if (start) {

            socket.connect()
            socket.on("connect", onConnectToModal);
            socket.on("disconnect", onDisconnectFromModal);
        }
        else {
            socket.disconnect()
        }

    }

        , [start])

    useEffect(() => {
        return () => clearInterval(intervalID)
    }, [intervalID])


    return (
        <>
            <div className="top-section flex">
                <div className="top-text flex">
                    <BackArrowList back={() => navigate(-1)} />
                    <h2 className="title">Поиск</h2>
                </div>
            </div>
            <p className=''>Нажмите “Начать”, когда будете готовы показывать жесты</p>
            <div className='flex'> 
                {words.map(word => (<VariantWord delete={deleteWord} id={word.id} key={word.id} text={word.text} />))}
            </div>
            <VideoCam></VideoCam>
            <button onClick={() => setStart(!start)}>{start ? "стоп" : "Начать"}</button>
        </>
    )
}

export default IVRSearchPage