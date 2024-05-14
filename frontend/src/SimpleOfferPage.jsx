import React, { useEffect, useRef, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import axios from 'axios';

const SimpleOfferPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [service, setService] = useState({})
  const id = useRef(location.state ? location.state.id : '0')

  useEffect(() => {
    
    axios.get(`http://localhost:8080/api/videoDoc/${id.current}`).then(res => res.data).then(data => setService(data))
    .catch(e => console.log(e))
  },[])
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
          {service.infoChildren && (
            <Link
              to={`/standart-list/${id}/info`}
              state={{ id: id, infCh: service.infoChildren}}
            >
              <button className="btn-reset btn-beige span-3">Подробнее</button>
            </Link>
          )}
          <button className="btn-reset btn-red span-2">Помощь</button>
        </div>
      </div>

      <div className="services-info-section flex">
        <p className="services-info-descr span-12">{service.textSimple}</p>
      </div>
    </>
  );
};

export default SimpleOfferPage;
