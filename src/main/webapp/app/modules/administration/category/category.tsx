import React, { useEffect } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getCategories } from 'app/modules/administration/category/category.reducer';
const Category = () => {
  const dispatch = useAppDispatch();
  const categoryList = useAppSelector(state => state.category.entities);
  useEffect(() => {
    dispatch(getCategories());
  }, [dispatch]);
  return (
    <div className="container mt-4">
      <h2>Quản lý danh mục</h2>
      <form className="mb-4">
        <div className="mb-2">
          <label className="form-label">Tên danh mục</label>
          <input type="text" className="form-control" placeholder="Nhập tên danh mục" />
        </div>
        <div className="mb-2">
          <label className="form-label">Mô tả</label>
          <textarea className="form-control" placeholder="Nhập mô tả"></textarea>
        </div>
        <button type="submit" className="btn btn-primary mt-2">
          Thêm danh mục
        </button>
      </form>
      <h4>Danh sách danh mục</h4>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Tên</th>
            <th>Mô tả</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {categoryList && categoryList.length > 0 ? (
            categoryList.map(category => (
              <tr key={category.id}>
                <td>{category.name}</td>
                <td>{category.description}</td>
                <td>
                  <button className="btn btn-warning btn-sm me-2">Sửa</button>
                  <button className="btn btn-danger btn-sm">Xóa</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={3} className="text-center">
                Không có danh mục nào
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Category;
