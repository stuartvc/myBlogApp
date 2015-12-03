class UsersController < ApplicationController
  skip_before_action :authenticate_user_with_token, :only => [:create]
  before_action :set_user, only: [:show]

  # GET /users
  # GET /users.json
  def index
    @users = User.all

    render json: @users, each_serializer: UserSerializer
  end

  # GET /users/1
  # GET /users/1.json
  def show
    render json: @user, serializer: UserSerializer
  end

  # POST /users
  # POST /users.json
  def create
    @user = User.new(user_params)

    if @user.save
      render json: { token: @user.auth_token }, status: :created, location: @user
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /users/1
  # PATCH/PUT /users/1.json
  def update
    if @current_user.update(user_params)
      head :no_content
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  # DELETE /users/1
  # DELETE /users/1.json
  def destroy
    @user.destroy

    head :no_content
  end

  private

    def set_user
      @user = User.find(params[:id])
    end

    def user_params
      params.require(:user).permit(:name, :email, :image_id)
    end
end
