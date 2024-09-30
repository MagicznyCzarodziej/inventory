import { View, Text, FlatList } from 'react-native';
import { useEffect, useState } from 'react';
import { IconButton } from 'react-native-paper';
import { useGetSponges } from '../../api/useGetSponges';
import { Page } from '../../layouts/Page';
import { Button } from '../../components/Button';
import { Colors } from '../../app/Theme';

export const SpongesPage = () => {
  const [sponges, setSponges] = useState<{ color: string, id: string, purpose: string }[]>([])

  const getSpongesQuery = useGetSponges()

  useEffect(() => {
    if (getSpongesQuery.isSuccess) {
      setSponges(getSpongesQuery.data.sponges)
    }
  }, [getSpongesQuery.isSuccess])

  return <Page
    safeArea={false}
  >
    <FlatList
      data={sponges}
      renderItem={(item) => {
        return <View style={{
          display: "flex",
          flexDirection: "row",
          backgroundColor: Colors.secondary,
          marginTop: 8,
          alignItems: 'center',
          gap: 16
        }}
        >
          <View
            style={{
              backgroundColor: `${item.item.color}`,
              width: 50,
              height: 50
            }}
          />
          <Text
            style={{
              fontSize: 20,
              color: Colors.text.main
            }}
          >
            {item.item.purpose}
          </Text>
          <IconButton
            icon={"delete"}
            iconColor={Colors.gray.light}
            style={{ marginLeft: "auto", opacity: 0.5 }}
          />
        </View>
      }}
    />
    <View style={{
      padding: 16
    }}>
      <Button
        onPress={() => {
        }}
        title={"Nowy zmywak"}
      />
    </View>
  </Page>
}