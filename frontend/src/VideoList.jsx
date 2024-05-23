import React, { useState, useEffect } from 'react'
import { useLocalObservable } from 'mobx-react-lite';
import { useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import VideoPost from './components/VideoPost';
import axios from 'axios';


const VideoList = () => {

    const location = useLocation()
    const url = location.state ? location.state.url : ''
    

    const [serviceList, setServiceList] = useState([])
    const [title, setTitle] = useState(location.state ? location.state.title : 'Услуги')

    const servicesHistory = useLocalObservable(() => ({
        history: [],

        addToHistory(newState) {
            this.history.push(newState);
        },

        getFromHistory() {
            return this.history.pop()
        }
    }))

    const titleHistory = useLocalObservable(() => ({
      history: [],
  
      addToHistory(newState) {
        this.history.push(newState);
      },
  
      getFromHistory() {
        return this.history.pop();
      },
    }));

    const navigate = useNavigate()

    const test = () => {
        if (servicesHistory.history.length > 0) {
            const prevState = servicesHistory.getFromHistory()
            const prevTitleState = titleHistory.getFromHistory()
            setTitle(prevTitleState)
            setServiceList(prevState)
        }
        else {
            navigate(-1)
        }
    }

    const goDeep = async (post) => {
      servicesHistory.addToHistory(serviceList);
      titleHistory.addToHistory(title)
      setTitle(post.textSimple)
      const address = post.children.join('&ids=')
      await axios.get(`http://localhost:8080/api/videoDoc?ids=${address}`)
      .then(res => res.data).then(data => setServiceList(data))
      .catch(e => console.log(e))
      }


    useEffect(() => {
      if (url) {
        axios.get(url).then(res => res.data).then(data=> setServiceList(data))
      } else {
        axios.get('http://localhost:8080/api/videoDoc/main').then(res => res.data).then(data => setServiceList(data))
        .catch(e => console.error(e))
      }}, [])
        
    

    return (
        <>
            <div className="top-section flex">
                <div className="top-text flex">
                    <BackArrowList back={() => test()} />
                    <h2 className="title">{title}</h2>
                    <h2 className="subtitle title">{serviceList.length} вариантов</h2>
                </div>
                <div className="btn-area flex">
                    <button onClick={() => navigate('/ivr-list/search')} className="btn-reset span-2 btn-beige">Поиск</button>
                    <button onClick={() => navigate('/choose')} className="btn-reset span-2 btn-brown">Язык</button>
                </div>
            </div>

            {
        serviceList.length ? (
          <div className="service-ivr-list col flex span-12">
            {serviceList.map(post => 
              post.children.length > 1 ? (
                <div key ={post.id} onClick={() => goDeep(post)}>
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