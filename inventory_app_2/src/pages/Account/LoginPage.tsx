import { Text, View, } from 'react-native';
import { useEffect, useState } from 'react';
import { Button } from "../../components/Button";
import { TextField } from '../../components/TextInput';
import { useLogin } from '../../api/useLogin';
import { AxiosError } from 'axios';
import { useNavigation } from '@react-navigation/native';
import { Page } from '../../layouts/Page';

export const LoginPage = () => {
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const loginMutation = useLogin()
  const loginError = getError(loginMutation.error)

  const { navigate } = useNavigation()

  useEffect(() => {
    if (loginMutation.status === "success") {
      navigate("INVENTORY", { screen: "INVENTORY_NAVIGATION" })
    }
  }, [loginMutation.status]);

  return <Page>
    <View
      style={{
        marginTop: "30%",
      }}
    >
      <Text
        style={{
          fontSize: 50,
          fontWeight: "bold",
          textAlign: "center"
        }}
      >
        Inventory
      </Text>
    </View>
    <View
      style={{
        marginTop: 58,
        paddingHorizontal: 48,
      }}
    >
      <TextField
        label="Nazwa uzytkownika"
        value={username}
        onChangeText={value => {
          setUsername(value)
          loginMutation.reset()
        }}
      />
      <TextField
        label="Hasło"
        secureTextEntry={true}
        onChangeText={value => {
          setPassword(value)
          loginMutation.reset()
        }}
        error={loginError}
      />
      <View style={{ marginTop: 16 }}>
        <Button
          title="Zaloguj"
          spinner={loginMutation.isPending}
          onPress={() => {
            loginMutation.mutate({ username, password })
          }}
        />
      </View>
    </View>
  </Page>
}

const getError = (error: Error | null) => {
  const axiosError = (error as AxiosError | null)
  if (axiosError?.isAxiosError) {
    if (axiosError.response?.status === 403) {
      return "Niepoprawna nazwa użytkownika lub hasło"
    }

    if (axiosError.code === "ERR_NETWORK") {
      return "Błąd połączenia"
    }
  }
}
