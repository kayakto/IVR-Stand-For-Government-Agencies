import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';

const IvrServicePage = () => {
  const location = useLocation();
  const id = location.state ? location.state.id : '0';
  const navigate = useNavigate();

  // поиск по айди в фетче
  const tempPost = {
    id: '2',
    parrent: '4',
    textSimple: 'Какая-то услуга',
    textRussian:
      'Тут рандомная гифка ссылкой ибо че то не подгружалось из папки',
    children: [],
    videoUrl:
      'https://avatars.dzeninfra.ru/get-zen_doc/1718701/pub_5e297d28df944400bd8adbd0_5e297f0143fdc000af4d03ba/orig',
    infoChild: ['1', '4', '3'],
  };
  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex">
          <BackArrowList
            back={() => {
              navigate(-1);
            }}
          />
          <h2 className="title">{tempPost.textSimple}</h2>
        </div>
      </div>
      <div className="ivr-info-section flex">
        <img src={tempPost.videoUrl} alt="" className="ivr-info-image span-6" />
        <div className="ivr-info-text span-6">
          <h3 className="ivr-info-title">Вам потребуется:</h3>
          <p className="ivr-info-descr">{tempPost.textRussian}</p>
        </div>
      </div>
      <div className="span-6 ivr-info-wrap flex">
        <button className="btn-reset btn-red span-3">Помощь</button>
        {tempPost.infoChild.length && (
          <Link
            to={`/ivr-list/${id}/info`}
            state={{ addInfo: tempPost.infoChild, id: tempPost.id }}
          >
            <button className="btn-reset btn-beige span-3">Подробнее</button>
          </Link>
        )}
      </div>
    </>
  );
};

export default IvrServicePage;
