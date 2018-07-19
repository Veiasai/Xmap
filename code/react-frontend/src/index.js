import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import {Provider} from 'mobx-react'
import UserData from './UserData'

ReactDOM.render(<Provider UserData={UserData}><App /></Provider>, document.getElementById('root'));
registerServiceWorker();
