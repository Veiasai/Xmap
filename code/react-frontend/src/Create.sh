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
import "./${fileName}.css"

class ${fileName} extends Component
{
    render()
    {
        return(
            
        )
    }
}

export default ${fileName};
EOF
touch "$fileName.css"