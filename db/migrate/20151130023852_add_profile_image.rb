class AddProfileImage < ActiveRecord::Migration
  def change
    add_reference :users, :image
  end
end
