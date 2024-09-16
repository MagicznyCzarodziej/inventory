import { Entry, ParentEntry } from '../../../api/item/useGetItems';

export const isParentEntry = (entry: Entry): entry is ParentEntry => Object.hasOwn(entry, "items")
