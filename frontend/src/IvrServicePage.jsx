import React, { useRef, useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import axios from 'axios';

const IvrServicePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const id = useRef(location.state ? location.state.id : '0')
  const [service, setService] = useState({})
  const [infoChild, setInfoChild] = useState([])

  useEffect(() => {
    
    axios.get(`https://pincode-dev.ru/ivr-hor/videoDoc/id/${id.current}`).then(res => res.data)
      .then(data => {
        if (data.infoChildren && data.infoChildren[0] != "null") {
          const infoChildrenURL = data.infoChildren.join("&ids=")
          axios.get(`https://pincode-dev.ru/ivr-hor/videoDoc?ids=${infoChildrenURL}`).then(response => response.data)
            .then(dataChild => setInfoChild(dataChild))
        }
        setService(data)
      })
      .catch(e => console.log(e))
  },[])

  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex">
          <BackArrowList
            back={() => {
              navigate(-1);
            }}
          />
          <h2 className="title">{location.state ? location.state.name : ''}</h2>
        </div>
      </div>
      <div className="ivr-info-section flex">
        <video autoPlay muted src={service.videoURL} alt="" className="ivr-info-image span-6" />
        <div className="ivr-info-text span-6">
          <p className="ivr-info-descr">{service.textSimple}</p>
        </div>
      </div>
      <div className="span-6 ivr-info-wrap flex">
        <button onClick={() => navigate('/choose')} className="btn-reset btn-brown span-3">Язык</button>
        {infoChild.length ? (
          <Link
            to={`/ivr-list/${id}/info`}
            state={{ info: infoChild, id: service.id }}
          >
            <button className="btn-reset btn-beige span-3">Подробнее</button>
          </Link>
        ) : <></>}
      </div>
    </>
  );
};

export default IvrServicePage;
