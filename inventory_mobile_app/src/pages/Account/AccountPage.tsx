import { useAuth } from '../../context/AuthContext';
import { useEffect } from 'react';
import { Page } from '../../layouts/Page';
import { Text, View } from 'react-native';
import { Colors } from '../../app/Theme';
import { Button } from '../../components/Button';
import { useLogout } from '../../api/useLogout';

export const AccountPage = () => {
  const { username, clearAuth } = useAuth();
  const logoutMutation = useLogout();

  useEffect(() => {
    if (logoutMutation.isSuccess) {
      clearAuth();
    }
  }, [logoutMutation.status]);

  return <Page>
    <View
      style={{
        marginTop: '30%',
      }}
    >
      <Text
        style={{
          fontSize: 50,
          color: Colors.text.main,
          fontWeight: 'bold',
          textAlign: 'center',
        }}
      >
        Inventory
      </Text>
    </View>
    <View
      style={{
        marginTop: 58,
        paddingHorizontal: 48,
        alignItems: 'center',
      }}
    >
      <View

        style={{
          backgroundColor: Colors.secondary,
          borderRadius: 50,
          width: 80,
          height: 80,
          justifyContent: 'center',
          alignItems: 'center',
          marginBottom: 24,
        }}>
        <Text style={{ fontSize: 36, color: Colors.primary, fontWeight: 'bold' }}>
          {username?.charAt(0).toUpperCase()}
        </Text>
      </View>

      <Text
        style={{
          fontSize: 24,
          color: Colors.text.main,
          fontWeight: '600',
          marginBottom: 8,
        }}>
        {username}
      </Text>

      <Text
        style={{
          fontSize: 14,
          color: Colors.text.gray,
          marginBottom: 40,
        }}>
        Zalogowano
      </Text>

      <View style={{ width: '100%' }}>
        <Button
          title="Wyloguj"
          onPress={() => {
            logoutMutation.mutate();
          }}
        />
      </View>
    </View>
  </Page>;
};
