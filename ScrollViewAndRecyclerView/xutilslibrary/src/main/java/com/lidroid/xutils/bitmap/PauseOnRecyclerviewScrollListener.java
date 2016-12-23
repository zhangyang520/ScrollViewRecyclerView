/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.lidroid.xutils.bitmap;

import android.support.v7.widget.RecyclerView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.task.TaskHandler;

public class PauseOnRecyclerviewScrollListener extends RecyclerView.OnScrollListener {

    private TaskHandler taskHandler;

    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final RecyclerView.OnScrollListener externalListener;

    /**
     * Constructor
     *
     * @param taskHandler   {@linkplain BitmapUtils} instance for controlling
     * @param pauseOnScroll Whether {@linkplain BitmapUtils#pause() pause loading} during touch scrolling
     * @param pauseOnFling  Whether {@linkplain BitmapUtils#pause() pause loading} during fling
     */
    public PauseOnRecyclerviewScrollListener(TaskHandler taskHandler, boolean pauseOnScroll, boolean pauseOnFling) {
        this(taskHandler, pauseOnScroll, pauseOnFling, null);
    }

    /**
     * Constructor
     *
     * @param taskHandler    {@linkplain BitmapUtils} instance for controlling
     * @param pauseOnScroll  Whether {@linkplain BitmapUtils#pause() pause loading} during touch scrolling
     * @param pauseOnFling   Whether {@linkplain BitmapUtils#pause() pause loading} during fling
     * @param customListener Your custom {@link } for {@linkplain   view} which also will
     *                       be get scroll events
     */
    public PauseOnRecyclerviewScrollListener(TaskHandler taskHandler, boolean pauseOnScroll, boolean pauseOnFling, RecyclerView.OnScrollListener customListener) {
        this.taskHandler = taskHandler;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        externalListener = customListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        switch (scrollState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                taskHandler.resume();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                if (pauseOnScroll) {
                    taskHandler.pause();
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                if (pauseOnFling) {
                    taskHandler.pause();
                }
                break;
        }
        if (externalListener != null) {
            externalListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScrolled(RecyclerView view, int dx,int dy) {
        if (externalListener != null) {
            externalListener.onScrolled(view, dx, dy);
        }
    }
}
