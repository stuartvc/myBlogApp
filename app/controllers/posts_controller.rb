class PostsController < ApplicationController
  before_action :set_post, only: [:show, :update, :destroy]

  # GET users/:user_id/posts
  # GET users/:user_id/posts.json
  def index
    @posts = @user.posts

    render json: @posts, each_serializer: PostPreviewSerializer
  end

  # GET /posts/1
  # GET /posts/1.json
  def show
    render json: @post, serializer: PostSerializer
  end

  # POST users/:user_id/posts
  # POST users/:user_id/posts.json
  def create
    @post = Post.new(post_params)

    if @post.save
      render json: @post, status: :created, location: @post
    else
      render json: @post.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /posts/1
  # PATCH/PUT /posts/1.json
  def update
    @post = Post.find(params[:id])

    if @post.update(post_params)
      head :no_content
    else
      render json: @post.errors, status: :unprocessable_entity
    end
  end

  # DELETE /posts/1
  # DELETE /posts/1.json
  def destroy
    @post.destroy

    head :no_content
  end

  private

    def set_post
      @post = Post.find(params[:id])
    end

    def post_params
      params.require(:post).permit(:title, :body, :user_id)
    end
end
