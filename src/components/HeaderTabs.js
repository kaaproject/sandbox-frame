import { Component } from 'react';
import FlatButton from 'material-ui/FlatButton';
import { Link } from 'react-router';

const styles = {
  base: {
    display: 'flex',
    alignItems: 'center',
    height: '100%',
  },
  label: {
    color: '#fff',
  },
};

class HeaderTabs extends Component {
  render() {
    return (
      <div style={styles.base}>
        <FlatButton
          containerElement={<Link to="/sandbox/managment" />}
          label="Managment"
          labelStyle={styles.label}
        />
      </div>
    );
  }
}

export default HeaderTabs;
