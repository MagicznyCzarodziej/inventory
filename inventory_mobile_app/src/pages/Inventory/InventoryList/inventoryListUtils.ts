import { Entry, ItemEntry } from '../../../api/useGetItems';
import { Colors } from '../../../app/Theme';
import { isParentEntry } from '../utils/itemsUtils';

export const getColor = (entry: ItemEntry) => {
  if (entry.currentStock < 1 && entry.desiredStock > 0)
    return Colors.status.red
  else if (entry.currentStock < entry.desiredStock)
    return Colors.status.orange
  else
    return Colors.status.green
}

export const flattenParentEntries = (entry: Entry) => {
  if (isParentEntry(entry)) {
    return entry.items.map(item => {
      return {
        ...item,
        name: `${entry.name} ${item.name}`
      }
    })
  } else {
    return [entry]
  }
}

export const sortEntries = (a: ItemEntry, b: ItemEntry) => {
  if (a.currentStock < a.desiredStock && b.currentStock < b.desiredStock) {
    if (a.currentStock == b.currentStock) {
      return b.desiredStock - a.desiredStock;
    } else if (a.currentStock < b.currentStock) {
      return -1;
    } else {
      return 1;
    }
  } else if (a.currentStock < a.desiredStock) {
    return -1;
  } else if (b.currentStock < b.desiredStock) {
    return 1;
  } else {
    if (a.desiredStock == 0) return 1;
    if (b.desiredStock == 0) return -1;

    return a.currentStock - b.currentStock;
  }
}
