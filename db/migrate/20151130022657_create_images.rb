class CreateImages < ActiveRecord::Migration
  def change
    create_table :images do |t|
      t.string :awsBucket
      t.string :awsKey

      t.timestamps null: false
    end
  end
end
