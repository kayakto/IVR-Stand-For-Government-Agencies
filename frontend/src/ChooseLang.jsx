import React from 'react';
import { Link } from 'react-router-dom';

const ChooseLang = () => {
  return (
    <>
      <h1 className="greeting">
        В данном сервисе доступно
        <br />
        распознавание русского жестового языка. <br />
        Пожалуйста, выберите способ взаимодействия
      </h1>
      <div className="flex choose_wrap">
        <Link
          to="/standart-list"
          className="choose-link-div choose-div-1 flex"
        >
          <button className="btn-reset btn-red">
            Простой язык
          </button>
        </Link>
        <Link
          to="/ivr-list"
          className="choose-link-div choose-div-2 flex"
        >
          <button className="btn-reset btn-red">
            Язык жестов
          </button>
        </Link>
      </div>
    </>
  );
};

export default ChooseLang;
