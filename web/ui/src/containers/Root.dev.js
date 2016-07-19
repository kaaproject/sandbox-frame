import { PropTypes, Component } from 'react';
import { Provider } from 'react-redux';
import { Router } from 'react-router';

import routes from '../routes';
import DevTools from './DevTools';

class Root extends Component {
  static propTypes = {
    history: PropTypes.object,
    store: PropTypes.object.isRequired,
  }

  render() {
    const { store, history } = this.props;

    return (
      <Provider store={store}>
        <div>
          <Router
            history={history}
            routes={routes}
          />
          <DevTools />
        </div>
      </Provider>
    );
  }
}

export default Root;
