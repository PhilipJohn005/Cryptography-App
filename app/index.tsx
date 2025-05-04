import React, { useState } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity } from 'react-native';
import AES from 'crypto-js/aes';
import { send } from 'react-native-sms';

const Index = () => {
  const [message, setMessage] = useState<string>('');
  const [phoneNumber, setPhoneNumber] = useState<string>('');

  const SECRET_KEY = 'nmit123securekey';

  const handlePhoneNumber = (text: string) => {
    if (/^\d{0,10}$/.test(text)) {
      setPhoneNumber(text);
    }
  };

  const encryptMessage = (message: string) => {
    return AES.encrypt(message, SECRET_KEY).toString();
  };

  const handleClick = () => {
    const encryptedMessage = encryptMessage(message);
    const phone = `+91${phoneNumber}`;

    console.log('Sending message to:', phone); 
    console.log('Encrypted message:', encryptedMessage);
    send(
      {
        body: encryptedMessage,
        recipients: [phone],
      },
      (completed, cancelled, error) => {
        if (completed) {
          console.log('SMS sent successfully');
        } else if (cancelled) {
          console.log('SMS sending was cancelled');
        } else if (error) {
          console.error('Failed to send SMS:', error);
        }
      }
    );
  };

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Cryptography App</Text>

      <TextInput
        style={styles.input}
        placeholder="Enter Message"
        value={message}
        onChangeText={setMessage}
      />
      <TextInput
        style={styles.input}
        value={phoneNumber}
        placeholder="Enter Phone number"
        keyboardType="phone-pad"
        onChangeText={handlePhoneNumber}
      />

      {phoneNumber.length === 10 && (
        <TouchableOpacity style={styles.sendButton} onPress={handleClick}>
          <Text style={styles.buttonText}>Send Message</Text>
        </TouchableOpacity>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    backgroundColor: '#bfdbfe',
    padding: 16,
  },
  text: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#f87171',
  },
  input: {
    borderWidth: 1,
    height: 40,
    margin: 12,
    padding: 10,
  },
  sendButton: {
    backgroundColor: '#4CAF50',
    padding: 10,
    borderRadius: 5,
    marginTop: 10,
  },
  buttonText: {
    color: 'white',
    textAlign: 'center',
    fontWeight: 'bold',
  },
});

export default Index;
