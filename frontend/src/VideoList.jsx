import React, { useState, useEffect } from 'react'
import { useLocalObservable } from 'mobx-react-lite';
import { useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import VideoPost from './components/VideoPost';


const VideoList = () => {

    const [serviceList, setServiceList] = useState([])

    const servicesHistory = useLocalObservable(() => ({
        history: [],

        addToHistory(newState) {
            this.history.push(newState);
        },

        getFromHistory() {
            return this.history.pop()
        }
    }))

    const navigate = useNavigate()

    const test = () => {
        if (servicesHistory.history.length > 0) {
            const prevState = servicesHistory.getFromHistory()
            setServiceList(prevState)
        }
        else {
            navigate(-1)
        }
    }

    const goDeep = () => {
        servicesHistory.addToHistory(serviceList)
        setServiceList(tempChild1)
      }

    const tempChild1 = [
        {
            id: '228',
            parrent: '4',
            textSimple: 'КАКОЙ ХОРОШИЙ ДЕНЬ',
            textRussian: 'чтобы подарить цветов',
            children: ['1488', '123'],
            videoUrl: 'src/img/gif.gif'
        }
    ]

    useEffect(() => {
        setServiceList([
            {
                id: '1',
                parrent: '',
                textSimple: 'Внесение денег на карту фронтендеру',
                textRussian: 'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
                children: ['2', '3'],
                videoUrl: 'src/img/gif.gif'
            },
            {
                id: '4',
                parrent: '',
                textSimple: 'Внесение денег на карту фронтендеру',
                textRussian: 'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
                children: ['2'],
                videoUrl: 'src/img/gif2.gif'
            }
        ])
    }, [])

    return (
        <>
            <div className="top-section flex">
                <div className="top-text flex">
                    <BackArrowList back={() => test()} />
                    <h2 className="title">Услуги</h2>
                    <h2 className="subtitle title">{serviceList.length} вариантов</h2>
                </div>
                <div className="btn-area flex">
                    <button className="btn-reset span-2 btn-beige">Поиск</button>
                    <button className="btn-reset span-2 btn-red">Помощь</button>
                </div>
            </div>

            {
        serviceList.length ? (
          <div className="service-ivr-list col flex span-12">
            {serviceList.map(post => 
              post.children.length > 1 ? (
                <div key ={post.id} onClick={() => goDeep()}>
                  <VideoPost data={post} childCount={post.children.length} />
                </div>
              ) : (<VideoPost key ={post.id} data={post} childCount={post.children.length} childId={post.children[0]}/>))
             }
          </div>
        ) :
          (
            <><h2 className='service-title'>Ничего не найдено</h2></>
          )
      }

        </>
    )
}

export default VideoList