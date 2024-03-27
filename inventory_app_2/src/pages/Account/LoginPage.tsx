import { Button, } from 'react-native';
import { TextInput } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { client } from '../../api/api';
import { useState } from 'react';

export const LoginPage = () => {
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const login = async () => {
    await client.post(
      "/auth/login",
      {
        username,
        password,
      })
  }

  return <SafeAreaView>
    <TextInput mode="outlined" label="Nazwa uzytkownika" value={username} onChangeText={setUsername} />
    <TextInput mode="outlined" label="Hasło" secureTextEntry={true} onChangeText={setPassword} />
    <Button title="Zaloguj" onPress={() => {
      login()
    }}/>
  </SafeAreaView>
}