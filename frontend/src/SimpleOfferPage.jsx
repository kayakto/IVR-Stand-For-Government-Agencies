import React, { useEffect, useRef, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import axios from 'axios';
import InfoItemsList from './components/InfoItemsList';

const SimpleOfferPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [service, setService] = useState({})
  const id = useRef(location.state ? location.state.id : '0')
  const [infoChild, setInfoChild] = useState([])

  useEffect(() => {

    axios.get(`https://pincode-dev.ru/ivr-hor/videoDoc/id/${id.current}`).then(res => res.data)
      .then(data => {
        if (data.infoChildren && data.infoChildren[0] != "null") {
          const infoChildrenURL = data.infoChildren.join("&ids=")
          console.log(data.infoChildren)
          axios.get(`https://pincode-dev.ru/ivr-hor/videoDoc/ids?ids=${infoChildrenURL}`).then(response => response.data)
            .then(dataChild => setInfoChild(dataChild))
        }
        setService(data)
      })
      .catch(e => console.log(e))
  }, [])


  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex span-12">
          <BackArrowList
            back={() => {
              navigate(-1);
            }}
          />
          <h2 className="title mr-auto">{location.state ? location.state.name : ''}</h2>
        </div>
        <div className="service-wrap flex">
          <button onClick={() => navigate('/choose')} className="btn-reset btn-brown span-2">Язык</button>
        </div>
      </div>

      <div className="services-info-section flex">
        <p className="services-info-descr span-12">{service.textSimple}</p>

        {infoChild.length ? 
          (<div>
            <h2 className='title'>Подробнее об услуге: </h2>
            <InfoItemsList id={service.id} info={infoChild} />
          </div>) : <></>
        }
      </div>
    </>
  );
};

export default SimpleOfferPage;
