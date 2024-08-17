import { Page } from '../../../layouts/Page';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { InventoryStackParamList } from '../InventoryList/InventoryNavigation';
import { Text } from 'react-native-paper';
import { EditablePhoto } from './EditablePhoto';
import { useGetItem } from '../../../api/useGetItem';
import { useEffect } from 'react';
import { useUploadPhoto } from '../../../api/useUploadPhoto';

type Props = NativeStackScreenProps<InventoryStackParamList, "EDIT_ITEM">;

export const ItemEditorPage = (props: Props) => {
  const itemId = props.route.params.itemId
  const { photoPath } = props.route.params

  const uploadPhotoMutation = useUploadPhoto()

  useEffect(() => {
    if (photoPath) {
      uploadPhotoMutation.mutate({ path: photoPath })
    }
  }, [photoPath]);

  if (!itemId) {
    return <Page>
      <Text>Brakuje itemId</Text>
    </Page>
  }

  const itemQuery = useGetItem(itemId)

  if (!itemQuery.isSuccess) {
    return <Page>
      <Text>Ładowanie</Text>
    </Page>
  }

  return <Page>
    <EditablePhoto
      originScreen="EDIT_ITEM"
      isLoading={uploadPhotoMutation.isPending}
      remotePhotoUrl={itemQuery.data.photoUrl}
      localPhotoPath={photoPath}
    />

    <Text>ItemEditorPage</Text>
  </Page>
}