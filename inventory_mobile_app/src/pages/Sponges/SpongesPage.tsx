import { View, Text, FlatList } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useContext, useEffect, useState } from 'react';
import { Appbar, Icon, IconButton } from 'react-native-paper';
import { DrawerContext } from '../../context/DrawerContext';
import chroma from 'chroma-js'
import { useGetSponges } from '../../api/useGetSponges';

export const SpongesPage = () => {
  const [sponges, setSponges] = useState<{ color: string, id: string, purpose: string }[]>([])
  const drawer = useContext(DrawerContext)

  const getSpongesQuery = useGetSponges()

  useEffect(() => {
    if (getSpongesQuery.isSuccess) {
      setSponges(getSpongesQuery.data.sponges)
    }
  }, [getSpongesQuery.isSuccess])

  return <SafeAreaView>
    <Appbar>
      <Appbar.Action icon="menu" onPress={() => {
        drawer.openDrawer()
      }} />
      <Appbar.Content title="Zmywaki" />
    </Appbar>

    <FlatList data={sponges} style={{ marginTop: 16, }} renderItem={(item) => {
      const color = chroma(item.item.color).luminance(0.4)
      const contrastToWhite = chroma.contrast(color, chroma("white"))
      const contrastToBlack = chroma.contrast(color, chroma("black"))
      const textColor = contrastToWhite > contrastToBlack ? "white" : "black"

      return <View style={{
        display: "flex",
        flexDirection: "row",
        backgroundColor: color.hex(),
        marginHorizontal: 16,
        marginVertical: 8,
        padding: 16,
        borderRadius: 10,
        alignItems: 'center',
        gap: 16
      }}>
        <View style={{ backgroundColor: `${item.item.color}`, width: 50, height: 50, borderRadius: 5 }} />
        <Text style={{ fontSize: 20, color: textColor }}>{item.item.purpose}</Text>
        <IconButton icon={"delete"} style={{ marginLeft: "auto", opacity: 0.5}} />
      </View>;
    }} />
    <View style={{
      display: "flex",
      flexDirection: "row",
      backgroundColor: '#E0E0E0',
      marginHorizontal: 16,
      marginVertical: 8,
      padding: 16,
      borderRadius: 10,
      alignItems: 'center',
      gap: 16,
    }}>
      <Icon size={24} source="plus" />
      <Text style={{ fontSize: 20 }}>Dodaj</Text>
    </View>
  </SafeAreaView>
}