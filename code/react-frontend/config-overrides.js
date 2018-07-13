const { injectBabelPlugin } = require('react-app-rewired');
const rewireMobX = require('react-app-rewire-mobx');

module.exports = function override(config, env) {
    // do stuff with the webpack config...
    config = injectBabelPlugin(
        ['import', { libraryName: 'antd', libraryDirectory: 'es', style: 'css' }], config);

    config = rewireMobX(config,env);

    return config;
};