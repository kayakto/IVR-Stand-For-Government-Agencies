import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import BackArrowList from './components/BackArrowList'

const InfoPage = () => {
  const location = useParams()
  const id = location.infid
  const navigate = useNavigate()

  const [text, setText] = useState('')

  useEffect(() => {
    axios.get(`https://pincode-dev.ru/ivr-hor/api/videoDoc/${id}`).then(res => res.data)
    .then(data => setText(data.textSimple))
  }, [id])

  return (
    <>
    <div className="top-section flex">
        <div className="top-text flex">
          <BackArrowList
            back={() => {
              navigate(-1);
            }}
          />
          <h2 className="title">Дополнительная информация</h2>
        </div>
      </div>
      <p>{text}</p>
    </>
    
  )
}

export default InfoPage