import { Component, inject, input, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommentResponse } from '../../../models/CommentResponse';
import { CommentsService } from '../../../services/comments.service';

@Component({
  selector: 'app-details',
  imports: [RouterLink],
  templateUrl: './details.html',
  styleUrl: './details.scss',
})
export class Details implements OnInit {
  selectedComment = signal<CommentResponse | null>(null);
  commentsService = inject(CommentsService);
  commentId = input<number>(0);
  newAnswerTitle = signal('');

  addAnswer(): void {
    this.commentsService
      .addAnswer({ commentId: this.commentId(), title: this.newAnswerTitle() })
      .subscribe((result) => {
        this.selectedComment.update((comment) => {
          if (!comment) {
            return comment;
          }
          return { ...comment, answers: [...comment.answers, result] };
        });
        this.newAnswerTitle.set('');
      });
  }

  ngOnInit(): void {
    this.commentsService.getCommentById(this.commentId()).subscribe((result) => {
      this.selectedComment.set(result);
    });
  }
}
