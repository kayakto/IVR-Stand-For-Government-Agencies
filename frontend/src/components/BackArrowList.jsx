import React from 'react'
import { Link } from 'react-router-dom'
import { observable } from 'mobx'

const BackArrowList = (props) => {
    return (
        <Link onClick={props.back} className="btn-back flex">
            <svg
                width="57"
                height="40"
                viewBox="0 0 57 40"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
            >
                <path
                    d="M53.5 20H4M4 20L20 4M4 20L20 36"
                    stroke="#2C2A29"
                    strokeWidth="7"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                />
            </svg>
        </Link>
    )
}

export default BackArrowList