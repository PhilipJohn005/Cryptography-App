import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const Index = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>Cryptography App</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#bfdbfe', 
    padding: 16, 
  },
  text: {
    fontSize: 20, 
    fontWeight: 'bold',
    color: '#f87171',
  },
});

export default Index;
