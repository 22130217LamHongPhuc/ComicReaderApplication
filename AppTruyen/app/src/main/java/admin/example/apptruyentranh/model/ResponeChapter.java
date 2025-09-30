package admin.example.apptruyentranh.model;



import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@Generated("jsonschema2pojo")
public class ResponeChapter implements Serializable {


    @SerializedName("data")
    @Expose
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    @Generated("jsonschema2pojo")
    public class ChapterImage implements Serializable{

        @SerializedName("image_page")
        @Expose
        private Integer imagePage;
        @SerializedName("image_file")
        @Expose
        private String imageFile;

        public Integer getImagePage() {
            return imagePage;
        }

        public void setImagePage(Integer imagePage) {
            this.imagePage = imagePage;
        }

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }

    }

    @Generated("jsonschema2pojo")
    class Data implements Serializable {

        @SerializedName("domain_cdn")
        @Expose
        private String domainCdn;
        @SerializedName("item")
        @Expose
        private ContentChapter item;

        public String getDomainCdn() {
            return domainCdn;
        }

        public void setDomainCdn(String domainCdn) {
            this.domainCdn = domainCdn;
        }

        public ContentChapter getChapter() {
            return item;
        }

        public void setItem(ContentChapter item) {
            this.item = item;
        }

    }




    @Generated("jsonschema2pojo")
    class ContentChapter implements Serializable{

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("comic_name")
        @Expose
        private String comicName;
        @SerializedName("chapter_name")
        @Expose
        private String chapterName;
        @SerializedName("chapter_title")
        @Expose
        private String chapterTitle;
        @SerializedName("chapter_path")
        @Expose
        private String chapterPath;
        @SerializedName("chapter_image")
        @Expose
        private List<ChapterImage> chapterImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComicName() {
            return comicName;
        }

        public void setComicName(String comicName) {
            this.comicName = comicName;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getChapterTitle() {
            return chapterTitle;
        }

        public void setChapterTitle(String chapterTitle) {
            this.chapterTitle = chapterTitle;
        }

        public String getChapterPath() {
            return chapterPath;
        }

        public void setChapterPath(String chapterPath) {
            this.chapterPath = chapterPath;
        }

        public List<ChapterImage> getChapterImage() {
            return chapterImage;
        }

        public void setChapterImage(List<ChapterImage> chapterImage) {
            this.chapterImage = chapterImage;
        }

    }

    public List<ChapterImage> getListContentChapter(){
        return data.getChapter().getChapterImage();
    }
    public String getPathImage(){
        return data.getChapter().getChapterPath();
    }

    public String getUrlImage(){
        return data.getDomainCdn()+"/"+data.getChapter().getChapterPath()+"/";
    }

    public String getPositionImage(int position){
        return data.getChapter().getChapterImage().get(position).getImageFile();
    }


    public String getNameChapter(){
        return data.getChapter().getChapterName();
    }


}



