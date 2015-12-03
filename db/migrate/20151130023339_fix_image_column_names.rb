class FixImageColumnNames < ActiveRecord::Migration
  def change
    rename_column :images, :awsBucket, :aws_bucket
    rename_column :images, :awsKey, :aws_key
  end
end
