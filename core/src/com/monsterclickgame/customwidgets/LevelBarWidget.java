package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class LevelBarWidget extends Widget {
	private Scaling scaling;
	
    private int align = Align.right;
    private float imageX;
    private float imageY;
    private float imageWidth;
    private float imageHeight;
    private float progress = 0f;
    
    private TextureRegion prog;
    private TextureRegion bg;
      
    public LevelBarWidget() {  
        this((TextureRegion) null, (TextureRegion) null);  
    }  
      
    public LevelBarWidget(TextureRegion prog, TextureRegion bg) {  
        this(prog, bg, Scaling.stretch, Align.right);
    }  
      
    public LevelBarWidget(TextureRegion prog, TextureRegion bg, Scaling scaling, int align) {
    	this.prog = prog;
    	this.bg = bg;
        this.scaling = scaling;  
        this.align = align;  
        setWidth(getPrefWidth());  
        setHeight(getPrefHeight());  
    }  
      
    @Override  
    public void draw(Batch batch, float parentAlpha) {  
        validate();  
  
        Color color = getColor();  
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);  
  
        float x = getX();  
        float y = getY();  
        float scaleX = getScaleX();  
        float scaleY = getScaleY();  
  
        if (prog != null && bg != null) {  
            float rotation = getRotation();  
            if (scaleX == 1 && scaleY == 1 && rotation == 0) {
                batch.draw(bg, x + imageX, y + imageY, imageWidth, imageHeight);  
            	batch.draw(prog, x + imageX, y + imageY, progress * imageWidth, imageHeight);
            } else {
            	batch.draw(bg, x + imageX, y + imageY, getOriginX() - imageX, getOriginY() - imageY, 
                		imageWidth, imageHeight, scaleX, scaleY, rotation); 
                batch.draw(prog, x + imageX, y + imageY, getOriginX() - imageX, getOriginY() - imageY, 
                		progress * imageWidth, imageHeight, scaleX, scaleY, rotation);  
            }  
        }
    }  
      
    @Override  
    public void layout() {  
        float regionWidth, regionHeight;  
        if (bg != null) {  
            regionWidth = bg.getRegionWidth();  
            regionHeight = bg.getRegionHeight();  
        } else  
            return;  
  
        float width = getWidth();  
        float height = getHeight();  
  
        Vector2 size = scaling.apply(regionWidth, regionHeight, width, height);  
        imageWidth = size.x;  
        imageHeight = size.y;  
  
        if ((align & Align.left) != 0)  
            imageX = 0;  
        else if ((align & Align.right) != 0)  
            imageX = width-imageWidth;  
        else  
            imageX = (width/2)-(imageWidth/2);  
  
        if ((align & Align.top) != 0)  
            imageY = height-imageHeight;  
        else if ((align & Align.bottom) != 0)  
            imageY = 0;  
        else  
            imageY = (height/2)-(imageHeight/2);  
    }  
  
    @Override  
    public void act(float delta) {  
        super.act(delta);
    }
      
    public void setImagemProgressao(TextureRegion prog) {  
        if (prog != null) {  
            if (this.prog == prog) return;  
            invalidateHierarchy();  
        } else {  
            if (getPrefWidth() != 0 || getPrefHeight() != 0) invalidateHierarchy();  
        }  
        this.prog = prog;  
    }
    
    public void setImagemBG(TextureRegion bg) {  
    	if (bg != null) {  
        	if (this.bg == bg) return;  
        	invalidateHierarchy();  
    	} else {  
        	if (getPrefWidth() != 0 || getPrefHeight() != 0) invalidateHierarchy();  
    	}  
    	this.bg = bg;  
    }  
  
    public void setScaling (Scaling scaling) {  
        if (scaling == null) throw new IllegalArgumentException("scaling cannot be null.");  
        this.scaling = scaling;  
    }  
  
    public void setAlign (int align) {  
        this.align = align;  
    }  
  
    public float getMinWidth () {  
        return 0;  
    }  
  
    public float getMinHeight () {  
        return 0;  
    }  
  
    public float getPrefWidth () {  
        if (bg != null) return bg.getRegionWidth();  
        return 0;  
    }  
  
    public float getPrefHeight () {  
        if (bg != null) return bg.getRegionHeight();  
        return 0;  
    }  
  
    public float getImageX () {  
        return imageX;  
    }  
  
    public float getImageY () {  
        return imageY;  
    }  
  
    public float getImageWidth () {  
        return imageWidth;  
    }  
  
    public float getImageHeight () {  
        return imageHeight;  
    }
    
    public void incrementProgress(float progress) {
    	if ((this.progress + progress) > 1)
    		this.progress = 1;
    	else
    		this.progress += progress;
    }
    
    public boolean decrementProgress(float progress) {
    	if ((this.progress - progress) < 0) {
    		this.progress = 0;
    		return false;
    	} else {
    		this.progress -= progress;
    		
    		if (this.progress == 0) {
    			return false;
    		} else {
    			return true;
    		}
    	}
    }
    
    public boolean isComplete() {
    	return (this.progress == 1); 
    }
    
    public void setProgress(float progress) {
    	if (progress > 1)
    		this.progress = 1;
    	else
    		this.progress = progress;
    }
    
    public float getProgress() {
    	return this.progress;
    }
}