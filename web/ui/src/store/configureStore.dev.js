import { applyMiddleware, compose, createStore } from 'redux';
import { apiMiddleware } from 'redux-api-middleware';
import createLogger from 'redux-logger';
import thunk from 'redux-thunk';

import DevTools from '../containers/DevTools';
import rootReducer from '../reducers';

export default function configureStore(preloadedState) {
  const store = createStore(
    rootReducer,
    preloadedState,
    compose(
      applyMiddleware(thunk, apiMiddleware, createLogger()),
      DevTools.instrument()
    )
  );

  if (module.hot) {
    // Enable Webpack hot module replacement for reducers
    module.hot.accept('../reducers', () => {
      const nextRootReducer = require('../reducers').default;// eslint-disable-line global-require

      store.replaceReducer(nextRootReducer);
    });
  }

  return store;
}
