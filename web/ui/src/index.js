import { render } from 'react-dom';
import { browserHistory } from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
import { syncHistoryWithStore } from 'react-router-redux';
import Root from './containers/Root';
import configureStore from './store/configureStore';


import './styles.less';// eslint-disable-line import/no-deprecated

const store = configureStore();
const history = syncHistoryWithStore(browserHistory, store, {
  selectLocationState(state) {
    return state.get('routing').toJS();
  },
});

/*
  we require next line to support onTouchTap event
  which will solve a lot of "click" problems for mobile devices
*/
injectTapEventPlugin();

render(
  <Root
    history={history}
    store={store}
  />,
  document.getElementById('content')
);
