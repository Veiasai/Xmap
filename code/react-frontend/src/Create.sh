fileName="$1"
echo "Create new component view: $fileName"
mkdir $fileName
cd $fileName
cat>index.js<<EOF
import view from "./view/${fileName}.js"
export {view}
EOF
mkdir view
cd view
cat>$fileName.js<<EOF
import React, { Component } from 'react';
import {} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./${fileName}.css"

@inject(['UserData'])
@observer
class ${fileName} extends Component
{
     constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
    }
    
    render()
    {
        return(
            <div>
            
            </div>
        )
    }
}

export default ${fileName};
EOF
touch "$fileName.css"