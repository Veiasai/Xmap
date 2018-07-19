import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {view as Navbar} from './Navbar'
import {view as Building} from './Components/Building'

class App extends Component {

    render() {
        return (
            <div>
                <Building />
            </div>
        );
    }
}

export default App;
