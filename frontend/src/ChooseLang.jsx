import React from 'react'
import { Link } from 'react-router-dom'

const ChooseLang = () => {
    return (
        <div className="wrap flex">
            <h1 className="greeting span-10">
                В данном сервисе доступно<br />
                распознавание русского жестового языка. <br />
                Пожалуйста, выберите способ взаимодействия
            </h1>
            <div className='flex choose_wrap'>
                <Link to='/standart-list' className='span-5 choose-link-div choose-div-1 flex'>
                    <button className='btn-reset span-5 btn-red to-bottom'>Простой язык</button>
                </Link>
                <Link to="/ivr-list" className='span-5 choose-link-div choose-div-2 flex'>
                    <button className='btn-reset span-5 btn-red to-bottom'>Язык жестов</button>
                </Link>
            </div>



        </div>

    )
}

export default ChooseLang