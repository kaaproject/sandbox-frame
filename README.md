
#  Copyright 2014-2016 CyberVision, Inc.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

# sandbox-frame


## Introduction

This is sandbox-frame written with React+Redux+Immutable+Webpack

## How to run?
1) npm install
2) to run dev build - npm start - go to localhost:3000
3) to get sources(production version) - npm run build, and go to folder dist
4) to run unit test - npm run test(npm test)
5) to run e2e test - npm run test:e2e
6) to get info about test coverage - npm run test:coverage

## About app
This application help users to start to work with KAA and to see some demo examples

## Architecture explanation

Structure of project was based on [**Wordpress** calypso](https://github.com/Automattic/wp-calypso) and [**Redux** real-world example](https://github.com/reactjs/redux/tree/master/examples/real-world) and [**Airbnb** ](https://github.com/airbnb/javascript)(tests, code style, etc.) and [**Docs**](http://redux.js.org/docs/introduction/index.html)

## Main features:
1. Ui have dump and clever components  [**Abramov proposal** ](https://medium.com/@dan_abramov/smart-and-dumb-components-7ca2f9a7c7d0#.jtuebwtpr)
2. [**Facebook Immutable** ](https://facebook.github.io/immutable-js/) to make work with data simpler ( [**redux-immutable**](https://github.com/indexiatech/redux-immutablejs) )
3. To work more simple with  [**middleware**](http://redux.js.org/docs/advanced/Middleware.html) and save time i use [**redux-api-middleware**](https://www.npmjs.com/package/redux-api-middleware)
4. To make clever components more simple i use connect from react-redux, instead of importing dispatch
5. [**mocha+enzym** ](https://github.com/airbnb/enzyme) for unti tests
6. [**Radium** ](https://github.com/FormidableLabs/radium) for styles(inline-styles instead of less(sass, css, etc.))
7. [**Normalizr** ](https://github.com/paularmstrong/normalizr) and [**reselect** ](https://github.com/reactjs/reselect) to prevent nesting, to have cheap caching, to make working with data simpler
8. [**pure render** ](https://www.npmjs.com/package/pure-render-decorator)
9. ES6+ES7 features

## TODO:
1. Create basic structure
2. Add design
3. Wrap all components, reducers, actions, etc. with unit tests to show more cases of testing redux app
4. Wrap all components with pure render(shouldComponentUpdate) for better perfomance
