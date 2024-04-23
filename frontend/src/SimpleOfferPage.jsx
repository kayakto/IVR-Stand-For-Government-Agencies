import React from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import BackArrowList from './components/BackArrowList'


const SimpleOfferPage = () => {
  const navigate = useNavigate()
  const location = useLocation()
  const id = location.state ? location.state.id : '0'
  const tempPost = {
    id: '2',
    parrent: '4',
    textSimple: 'Какая-то там услуга',
    textRussian: "Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, sit amet consectetur adipisicing elit. Facere quidem temporibus voluptatibus explicabo obcaecati corrupti iusto dolorem illo delectus pariatur aperiam eius, omnis vel numquam ab adipisci sequi totam unde!",
    children: [],
    videoUrl: 'https://avatars.dzeninfra.ru/get-zen_doc/1718701/pub_5e297d28df944400bd8adbd0_5e297f0143fdc000af4d03ba/orig',
    infoChild: ['1', '4', '3']
  }

  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex span-12">
          <BackArrowList back={() => {
            navigate(-1)
          }} />
          <h2 className="title mr-auto">{tempPost.textSimple}</h2>
        </div>
        <div className="service-wrap flex">
            {tempPost.infoChild.length && (
              <Link to={`/standart-list/${id}/info`} state={{id: id, infCh: tempPost.infoChild}}>
                <button className='btn-reset btn-beige span-3'>Подробнее</button>
              </Link>
            )}
            <button className='btn-reset btn-red span-2'>Помощь</button>
          </div>
      </div>

      <div className='services-info-section flex'>
            <p className='services-info-descr span-12'>{tempPost.textRussian}</p>
      </div>
    </>
  )
}

export default SimpleOfferPage