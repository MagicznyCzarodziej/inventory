import { View, Text, FlatList } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { client } from '../../api/api';
import { useContext, useEffect, useState } from 'react';
import { AxiosError } from 'axios';
import { Appbar } from 'react-native-paper';
import { DrawerContext } from '../../context/DrawerContext';

export const SpongesPage = () => {
  // const query = useQuery({
  //   queryKey: ['todos'], queryFn: () => {
  //     return client.get("/sponges")
  //   },
  //   refetchOnMount: false,
  // })
  //

  const [sponges, setSponges] = useState<{color: string, id: string, purpose: string}[]>([])
  const drawer = useContext(DrawerContext)

  const getSponges = async () => {
    return client.get(
      "/sponges",
    )
  }

  useEffect(() => {
    const x = async () => {
      try {
        const s = await getSponges()
        console.log(s.data)
        setSponges(s.data.sponges)

      } catch (e: AxiosError) {
        console.log(e.message)
      }
    }
    x().then()
  }, [])

  return <SafeAreaView>
    <Appbar>
      <Appbar.Action icon="menu" onPress={() => {
        drawer.openDrawer()
      }} />
      <Appbar.Content title="Zmywaki" />
    </Appbar>

    <FlatList data={sponges} renderItem={(item) => <View style={{display: "flex", flexDirection: "row"}}>
      <Text>{item.item.purpose}</Text>
      <View style={{backgroundColor: `#${item.item.color}`, width: 20, height: 20}} />
    </View>} />
  </SafeAreaView>
}