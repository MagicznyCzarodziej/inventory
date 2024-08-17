import React from 'react';
import { RemoteItemPhoto } from './RemoteItemPhoto';
import { PhotoPreview } from './PhotoPreview';

interface Props {
  remotePhotoUrl: string | null
  localPhotoPath?: string
  isUploading: boolean;
}

export const ItemPhoto = (props: Props) => {
  const { remotePhotoUrl, localPhotoPath, isUploading } = props;

  if (localPhotoPath) {
    return (
      <PhotoPreview photoUri={localPhotoPath} isBlurred={isUploading} isLoading={isUploading} />
    )
  }

  if (remotePhotoUrl) {
    return <RemoteItemPhoto photoUrl={remotePhotoUrl} />
  }

  return null;
}
