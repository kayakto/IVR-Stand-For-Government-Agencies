import axios from 'axios'
import React, { useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

const SimpleSearch = (props) => {
    const {setOffers, servicesHistory, titleHistory, setTitle, offers, title} = props
    const [searching, setSearching] = useState()
    const search = () => {
        axios.get(`http://localhost:8080/api/videoDoc/search/${searching}`).then(res => res.data)
        .then(data => {
            servicesHistory.addToHistory(offers)
            titleHistory.addToHistory(title)
            setTitle(searching)
            setOffers(data)
        })

    }
  return (
    
    <div className='flex'>
        <input type="text" placeholder='Поиск' onChange={(e) => setSearching(e.target.value)}/>
        <button onClick={search}>Найти</button>
    </div>
  )
}

export default SimpleSearch