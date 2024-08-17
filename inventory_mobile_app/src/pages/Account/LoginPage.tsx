import { Text, View, } from 'react-native';
import { useEffect, useState } from 'react';
import { Button } from "../../components/Button";
import { TextField } from '../../components/TextInput';
import { useLogin } from '../../api/useLogin';
import { AxiosError } from 'axios';
import { Page } from '../../layouts/Page';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { InventoryStackParamList } from '../Inventory/InventoryList/InventoryNavigation';
import { CompositeScreenProps } from '@react-navigation/native';
import { RootStackParamList } from '../../app/Root';

type Props = CompositeScreenProps<
  NativeStackScreenProps<RootStackParamList, "ACCOUNT">,
  NativeStackScreenProps<InventoryStackParamList, "INVENTORY_LIST">
>

export const LoginPage = (props: Props) => {
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const loginMutation = useLogin()
  const loginError = getError(loginMutation.error)

  const { navigate } = props.navigation

  useEffect(() => {
    if (loginMutation.status === "success") {
      navigate("INVENTORY_LIST")
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
        autoCapitalize="none"
        onChangeText={value => {
          setUsername(value)
          loginMutation.reset()
        }}
      />
      <TextField
        label="Hasło"
        secureTextEntry={true}
        autoCapitalize="none"
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
