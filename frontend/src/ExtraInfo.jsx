import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import { Link } from 'react-router-dom';
import axios from 'axios';
import InfoItemsList from './components/InfoItemsList';

const ExtraInfo = () => {
  const location = useLocation();
  const id = location.state ? location.state.id : '0';
  const infoChilds = location.state ? location.state.info : []

  const navigate = useNavigate();


  
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
      <InfoItemsList id={id} info={infoChilds}></InfoItemsList>
    </>
  );
};

export default ExtraInfo;
