import { Component } from 'react';
import AppBar from 'material-ui/AppBar';

import HeaderTabs from './HeaderTabs';

const styles = {
  bar: {
    margin: 0,
  },
  title: {},
};

class NavigationHeader extends Component {
  render() {
    return (
      <div>
        <AppBar
          style={styles.bar}
          title={<HeaderTabs />}
        />
      </div>
    );
  }
}

export default NavigationHeader;
