package com.codo.amber_sleepeanuty.module_chat.widget;

import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public interface ViewHolderHelper {
    void HandleVoiceClickEvent(EMVoiceMessageBody body);
    void HandleImgClickEvent(EMImageMessageBody body);
}
