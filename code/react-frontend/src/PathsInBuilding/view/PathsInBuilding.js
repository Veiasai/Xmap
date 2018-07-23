import React, { Component } from 'react';
import {} from 'antd';
import "./PathsInBuilding.css"
import {httpHead} from "../../Consts";

class PathsInBuilding extends Component
{

    getBuildingPathList = async () => {
        const url = httpHead + '';
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                    body: this.UserData.currentBuilding.ID,
                });
            const json = await response.json();

            if (json.code === 200) {
                this.UserData.currentPathList = json.pathList;
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    }

    render()
    {
        return(
            <div>

            </div>
            
        )
    }
}

export default PathsInBuilding;
