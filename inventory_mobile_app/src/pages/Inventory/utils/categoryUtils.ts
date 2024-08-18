import { Category } from '../../../api/common';
import { useGetCategories } from '../../../api/useGetCategories';
import { useEffect, useState } from 'react';

export const mapCategoriesToCategorySelectItems = (categories?: Category[]) => categories?.map(category => ({
  value: category.id,
  label: category.name
})) ?? []

export const useCategorySelect = () => {
  const [categoryId, setCategoryId] = useState<string>();

  const categoriesQuery = useGetCategories();

  // Set initial category
  useEffect(() => {
    if (categoriesQuery.isSuccess) {
      setCategoryId(categoriesQuery.data.categories[0].id)
    }
  }, [categoriesQuery.isSuccess]);

  const categorySelectItems = mapCategoriesToCategorySelectItems(categoriesQuery.data?.categories)

  return {
    categoryId,
    setCategoryId,
    categorySelectItems,
  }
}