import { PropTypes, Component } from 'react';
import { Provider } from 'react-redux';
import { Router } from 'react-router';

import routes from '../routes';

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
        </div>
      </Provider>
    );
  }
}

export default Root;
