import { Component, OnInit, signal } from '@angular/core';
import { CommentsService } from '../../services/comments.service';
import { CommentResponse } from '../../models/CommentResponse';
import { Comment } from "./comment/comment";

@Component({
  selector: 'app-comments',
  imports: [Comment],
  templateUrl: './comments.html',
  styleUrl: './comments.scss',
})
export class Comments implements OnInit {
  constructor(private commentsService: CommentsService) { }

  comments = signal<CommentResponse[]>([]);

  ngOnInit(): void {
    this.commentsService.getAll().subscribe((comments) => {
      this.comments.set(comments);
    });
  }
}
