


<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Task Title</th>
                            <td><?php echo ucfirst($task['title']); ?></td>
                        </tr>
                        <tr>
                            <th>Task Category</th>
                            <td><?php echo ucfirst($taskCategory['cat_name']); ?></td>
                        </tr>
                        <tr>
                            <th>Task Location</th>
                            <td><?php echo $task['location']; ?></td>
                        </tr>
                        <tr>
                            <th>Task Description</th>
                            <td><?php echo ucfirst($task['description']); ?></td>
                        </tr>
                        <tr>
                            <th>Task Work Mode</th>
                            <td><?php echo ucfirst($task['work_mode']); ?></td>
                        </tr>
                        <tr>
                            <th>Task Online Status</th>
                            <td><?php echo ucfirst($task['completed_online']); ?></td>
                        </tr>
                        <tr>
                            <th>Task Date</th>
                            <td><?php echo $task['due_date']; ?></td>
                        </tr>
                        <tr>
                            <th>Task Poster</th>
                            <td><?php echo $taskPoster['first_name'] . ' ' . $taskPoster['last_name']; ?></td>
                        </tr>

                        <tr>
                            <th>Task Status</th>

                            <td><?php
                                if ($task['task_status'] == 0) {
                                    echo 'Open';
                                } elseif ($task['task_status'] == 1) {
                                    echo 'Assigned';
                                } else {
                                    echo 'Completed ';
                                }
                                ?></td>

                        </tr>
                        <tr>
                            <th>Status</th>

                            <td><?php
                                if ($task['status'] == 1) {
                                    echo 'Active';
                                } else {
                                    echo 'Deactive';
                                }
                                ?></td>

                        </tr>

                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->

