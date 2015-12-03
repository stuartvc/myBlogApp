require 'test_helper'

class ImagesControllerTest < ActionController::TestCase
  setup do
    @image = images(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:images)
  end

  test "should create image" do
    assert_difference('Image.count') do
      post :create, image: { awsBucket: @image.awsBucket, awsKey: @image.awsKey }
    end

    assert_response 201
  end

  test "should show image" do
    get :show, id: @image
    assert_response :success
  end

  test "should update image" do
    put :update, id: @image, image: { awsBucket: @image.awsBucket, awsKey: @image.awsKey }
    assert_response 204
  end

  test "should destroy image" do
    assert_difference('Image.count', -1) do
      delete :destroy, id: @image
    end

    assert_response 204
  end
end
