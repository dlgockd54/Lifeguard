package com.example.hclee.lifeguard.taskmanager

import com.example.hclee.lifeguard.AndroidThings

/**
 * Created by hclee on 2019-04-11.
 */

interface TaskManager {
    fun runTask(androidThings: AndroidThings)
}